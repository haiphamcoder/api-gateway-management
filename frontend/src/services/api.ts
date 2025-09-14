import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/';

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Request interceptor to add auth token
apiClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('accessToken') || sessionStorage.getItem('accessToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error instanceof Error ? error : new Error(String(error)));
    }
);

// Response interceptor to handle token refresh
apiClient.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            try {
                const refreshToken = localStorage.getItem('refreshToken') || sessionStorage.getItem('refreshToken');
                if (refreshToken) {
                    const response = await axios.post(`${API_BASE_URL}/auth/refresh`, {
                        refreshToken,
                    });

                    const { accessToken } = response.data.data;
                    localStorage.setItem('accessToken', accessToken);
                    sessionStorage.setItem('accessToken', accessToken);

                    originalRequest.headers.Authorization = `Bearer ${accessToken}`;
                    return apiClient(originalRequest);
                }
            } catch (refreshError) {
                // Refresh failed, redirect to login
                console.error('Token refresh failed:', refreshError);
                localStorage.clear();
                sessionStorage.clear();
                window.location.href = '/login';
            }
        }

        return Promise.reject(error instanceof Error ? error : new Error(String(error)));
    }
);

// Auth API
export const authApi = {
    login: (data: { email: string; password: string; rememberMe: boolean }) =>
        apiClient.post('/auth/login', data),

    refresh: (data: { refreshToken: string }) =>
        apiClient.post('/auth/refresh', data),

    logout: () =>
        apiClient.post('/auth/logout'),

};

// Services API
export const servicesApi = {
    getServices: (params?: { search?: string; page?: number; size?: number }) =>
        apiClient.get('/services', { params }),

    getService: (id: string) =>
        apiClient.get(`/services/${id}`),

    createService: (data: any) =>
        apiClient.post('/services', data),

    updateService: (id: string, data: any) =>
        apiClient.patch(`/services/${id}`, data),

    deleteService: (id: string) =>
        apiClient.delete(`/services/${id}`),

};

// Routes API
export const routesApi = {
    getRoutes: (serviceId: string) =>
        apiClient.get(`/services/${serviceId}/routes`),

    getRoute: (id: string) =>
        apiClient.get(`/routes/${id}`),

    createRoute: (serviceId: string, data: any) =>
        apiClient.post(`/services/${serviceId}/routes`, data),

    updateRoute: (id: string, data: any) =>
        apiClient.patch(`/routes/${id}`, data),

    deleteRoute: (id: string) =>
        apiClient.delete(`/routes/${id}`),
};

export default apiClient;