import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';

const resources = {
  en: {
    translation: {
      // Common
      common: {
        save: 'Save',
        cancel: 'Cancel',
        delete: 'Delete',
        edit: 'Edit',
        create: 'Create',
        search: 'Search',
        loading: 'Loading...',
        error: 'Error',
        success: 'Success',
        confirm: 'Confirm',
        close: 'Close',
        back: 'Back',
        next: 'Next',
        previous: 'Previous',
        actions: 'Actions',
        name: 'Name',
        email: 'Email',
        password: 'Password',
        status: 'Status',
        created: 'Created',
        updated: 'Updated',
        owner: 'Owner',
      },
      
      // Auth
      auth: {
        login: 'Login',
        logout: 'Logout',
        forgotPassword: 'Forgot Password?',
        rememberMe: 'Remember me',
        resetPassword: 'Reset Password',
        newPassword: 'New Password',
        confirmPassword: 'Confirm Password',
        loginTitle: 'Sign in to your account',
        loginSubtitle: 'Welcome back! Please enter your details.',
        resetPasswordTitle: 'Reset your password',
        resetPasswordSubtitle: 'Enter your new password below.',
        invalidCredentials: 'Invalid email or password',
        loginSuccess: 'Login successful',
        logoutSuccess: 'Logout successful',
        resetEmailSent: 'Password reset email sent',
        passwordResetSuccess: 'Password reset successful',
      },
      
      // Navigation
      nav: {
        dashboard: 'Dashboard',
        services: 'Services',
        routes: 'Routes',
        upstreams: 'Upstreams',
        targets: 'Targets',
        settings: 'Settings',
      },
      
      // Dashboard
      dashboard: {
        title: 'Dashboard',
        welcome: 'Welcome to API Gateway Management',
        stats: {
          totalServices: 'Total Services',
          totalRoutes: 'Total Routes',
          totalUpstreams: 'Total Upstreams',
          totalTargets: 'Total Targets',
        },
        quickActions: 'Quick Actions',
        recentActivity: 'Recent Activity',
      },
      
      // Services
      services: {
        title: 'Services',
        addService: 'Add Service',
        serviceName: 'Service Name',
        backendUrl: 'Backend URL',
        connectTimeout: 'Connect Timeout (ms)',
        writeTimeout: 'Write Timeout (ms)',
        readTimeout: 'Read Timeout (ms)',
        retries: 'Retries',
        nameValidation: 'Service name must contain only alphanumeric characters, dots, and hyphens',
        urlValidation: 'Please enter a valid URL',
        timeoutValidation: 'Timeout must be between 1 and 120000 milliseconds',
        retriesValidation: 'Retries must be between 0 and 10',
        serviceCreated: 'Service created successfully',
        serviceUpdated: 'Service updated successfully',
        serviceDeleted: 'Service deleted successfully',
        noServices: 'No services found',
        searchPlaceholder: 'Search services...',
      },
      
      // Routes
      routes: {
        title: 'Routes',
        addRoute: 'Add Route',
        paths: 'Paths',
        methods: 'HTTP Methods',
        stripPath: 'Strip Path',
        preserveHost: 'Preserve Host',
        requestBuffering: 'Request Buffering',
        responseBuffering: 'Response Buffering',
        pathValidation: 'Paths must start with /',
        methodValidation: 'Please select at least one HTTP method',
        routeCreated: 'Route created successfully',
        routeUpdated: 'Route updated successfully',
        routeDeleted: 'Route deleted successfully',
        noRoutes: 'No routes found',
        performanceTip: 'Disable buffering if latency is high',
      },
      
      // Upstreams
      upstreams: {
        title: 'Upstreams',
        addUpstream: 'Add Upstream',
        slots: 'Slots',
        healthChecks: 'Health Checks',
        activeChecks: 'Active Checks',
        passiveChecks: 'Passive Checks',
        upstreamCreated: 'Upstream created successfully',
        upstreamUpdated: 'Upstream updated successfully',
        upstreamDeleted: 'Upstream deleted successfully',
        noUpstreams: 'No upstreams found',
        searchPlaceholder: 'Search upstreams...',
      },
      
      // Targets
      targets: {
        title: 'Targets',
        addTarget: 'Add Target',
        target: 'Target',
        weight: 'Weight',
        weightDisabled: 'Weight 0 disables the target',
        targetCreated: 'Target created successfully',
        targetUpdated: 'Target updated successfully',
        targetDeleted: 'Target deleted successfully',
        noTargets: 'No targets found',
        activeTargets: 'Active Targets',
      },
    },
  },
  vi: {
    translation: {
      // Common
      common: {
        save: 'Lưu',
        cancel: 'Hủy',
        delete: 'Xóa',
        edit: 'Sửa',
        create: 'Tạo',
        search: 'Tìm kiếm',
        loading: 'Đang tải...',
        error: 'Lỗi',
        success: 'Thành công',
        confirm: 'Xác nhận',
        close: 'Đóng',
        back: 'Quay lại',
        next: 'Tiếp theo',
        previous: 'Trước',
        actions: 'Hành động',
        name: 'Tên',
        email: 'Email',
        password: 'Mật khẩu',
        status: 'Trạng thái',
        created: 'Tạo lúc',
        updated: 'Cập nhật lúc',
        owner: 'Chủ sở hữu',
      },
      
      // Auth
      auth: {
        login: 'Đăng nhập',
        logout: 'Đăng xuất',
        forgotPassword: 'Quên mật khẩu?',
        rememberMe: 'Ghi nhớ đăng nhập',
        resetPassword: 'Đặt lại mật khẩu',
        newPassword: 'Mật khẩu mới',
        confirmPassword: 'Xác nhận mật khẩu',
        loginTitle: 'Đăng nhập vào tài khoản',
        loginSubtitle: 'Chào mừng trở lại! Vui lòng nhập thông tin của bạn.',
        resetPasswordTitle: 'Đặt lại mật khẩu',
        resetPasswordSubtitle: 'Nhập mật khẩu mới của bạn bên dưới.',
        invalidCredentials: 'Email hoặc mật khẩu không đúng',
        loginSuccess: 'Đăng nhập thành công',
        logoutSuccess: 'Đăng xuất thành công',
        resetEmailSent: 'Email đặt lại mật khẩu đã được gửi',
        passwordResetSuccess: 'Đặt lại mật khẩu thành công',
      },
      
      // Navigation
      nav: {
        dashboard: 'Bảng điều khiển',
        services: 'Dịch vụ',
        routes: 'Tuyến đường',
        upstreams: 'Upstream',
        targets: 'Targets',
        settings: 'Cài đặt',
      },
      
      // Dashboard
      dashboard: {
        title: 'Bảng điều khiển',
        welcome: 'Chào mừng đến với API Gateway Management',
        stats: {
          totalServices: 'Tổng dịch vụ',
          totalRoutes: 'Tổng tuyến đường',
          totalUpstreams: 'Tổng upstream',
          totalTargets: 'Tổng targets',
        },
        quickActions: 'Hành động nhanh',
        recentActivity: 'Hoạt động gần đây',
      },
      
      // Services
      services: {
        title: 'Dịch vụ',
        addService: 'Thêm dịch vụ',
        serviceName: 'Tên dịch vụ',
        backendUrl: 'URL Backend',
        connectTimeout: 'Thời gian chờ kết nối (ms)',
        writeTimeout: 'Thời gian chờ ghi (ms)',
        readTimeout: 'Thời gian chờ đọc (ms)',
        retries: 'Số lần thử lại',
        nameValidation: 'Tên dịch vụ chỉ được chứa ký tự chữ cái, số, dấu chấm và dấu gạch ngang',
        urlValidation: 'Vui lòng nhập URL hợp lệ',
        timeoutValidation: 'Thời gian chờ phải từ 1 đến 120000 mili giây',
        retriesValidation: 'Số lần thử lại phải từ 0 đến 10',
        serviceCreated: 'Tạo dịch vụ thành công',
        serviceUpdated: 'Cập nhật dịch vụ thành công',
        serviceDeleted: 'Xóa dịch vụ thành công',
        noServices: 'Không tìm thấy dịch vụ nào',
        searchPlaceholder: 'Tìm kiếm dịch vụ...',
      },
      
      // Routes
      routes: {
        title: 'Tuyến đường',
        addRoute: 'Thêm tuyến đường',
        paths: 'Đường dẫn',
        methods: 'Phương thức HTTP',
        stripPath: 'Bỏ đường dẫn',
        preserveHost: 'Giữ nguyên Host',
        requestBuffering: 'Đệm yêu cầu',
        responseBuffering: 'Đệm phản hồi',
        pathValidation: 'Đường dẫn phải bắt đầu bằng /',
        methodValidation: 'Vui lòng chọn ít nhất một phương thức HTTP',
        routeCreated: 'Tạo tuyến đường thành công',
        routeUpdated: 'Cập nhật tuyến đường thành công',
        routeDeleted: 'Xóa tuyến đường thành công',
        noRoutes: 'Không tìm thấy tuyến đường nào',
        performanceTip: 'Tắt đệm nếu độ trễ cao',
      },
      
      // Upstreams
      upstreams: {
        title: 'Upstream',
        addUpstream: 'Thêm upstream',
        slots: 'Slots',
        healthChecks: 'Kiểm tra sức khỏe',
        activeChecks: 'Kiểm tra chủ động',
        passiveChecks: 'Kiểm tra thụ động',
        upstreamCreated: 'Tạo upstream thành công',
        upstreamUpdated: 'Cập nhật upstream thành công',
        upstreamDeleted: 'Xóa upstream thành công',
        noUpstreams: 'Không tìm thấy upstream nào',
        searchPlaceholder: 'Tìm kiếm upstream...',
      },
      
      // Targets
      targets: {
        title: 'Targets',
        addTarget: 'Thêm target',
        target: 'Target',
        weight: 'Trọng số',
        weightDisabled: 'Trọng số 0 sẽ tắt target',
        targetCreated: 'Tạo target thành công',
        targetUpdated: 'Cập nhật target thành công',
        targetDeleted: 'Xóa target thành công',
        noTargets: 'Không tìm thấy target nào',
        activeTargets: 'Targets hoạt động',
      },
    },
  },
};

i18n
  .use(initReactI18next)
  .init({
    resources,
    lng: 'en',
    fallbackLng: 'en',
    interpolation: {
      escapeValue: false,
    },
  });

export default i18n;
