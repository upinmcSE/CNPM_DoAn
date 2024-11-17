import React, { createContext, useState, useCallback } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem('authToken'));

    const login = useCallback((token) => {
        localStorage.setItem('authToken', token);
        setIsLoggedIn(true);
    }, []);

    const logout = useCallback(() => {
        localStorage.removeItem('authToken');
        setIsLoggedIn(false);
    }, []);

    return (
        <AuthContext.Provider value={{ isLoggedIn, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};