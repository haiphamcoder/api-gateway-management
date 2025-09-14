import { createContext, useContext, useEffect, useState, useMemo } from "react";
import { useNavigate } from "react-router-dom";
import { authApi } from "../services/api";
import toast from "react-hot-toast";

interface User {
    id: number;
    email: string;
    name: string;
    locale: string;
    roles: string[];
}

interface AuthContextType {
    user: User | null;
    token: string | null;
    login: (email: string, password: string, rememberMe: boolean) => Promise<void>;
    logout: () => void;
    isLoading: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (context === undefined) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};

interface AuthProviderProps {
    children: React.ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    const [user, setUser] = useState<User | null>(null);
    const [token, setToken] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const storedToken = localStorage.getItem('accessToken');
        const storedUser = localStorage.getItem('user');

        if (storedToken && storedUser) {
            setToken(storedToken);
            setUser(JSON.parse(storedUser));
        }

        setIsLoading(false);
    }, []);

    const login = async (email: string, password: string, rememberMe: boolean) => {
        try {
            const response = await authApi.login({ email, password, rememberMe });
            const { accessToken, refreshToken, user: userData } = response.data;

            setToken(accessToken);
            setUser(userData);

            if (rememberMe) {
                localStorage.setItem('accessToken', accessToken);
                localStorage.setItem('refreshToken', refreshToken);
                localStorage.setItem('user', JSON.stringify(userData));
            } else {
                sessionStorage.setItem('accessToken', accessToken);
                sessionStorage.setItem('refreshToken', refreshToken);
                sessionStorage.setItem('user', JSON.stringify(userData));
            }

            navigate('/dashboard');
        } catch (error) {
            console.error('Login failed:', error);
            toast.error('Invalid email or password');
            throw error;
        }
    };

    const logout = () => {
        setUser(null);
        setToken(null);

        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        localStorage.removeItem('user');
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('refreshToken');
        sessionStorage.removeItem('user');
        navigate('/login');
    };

    const value = useMemo(() => ({
        user,
        token,
        login,
        logout,
        isLoading,
    }), [user, token, login, logout, isLoading]);

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}