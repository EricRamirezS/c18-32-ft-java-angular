import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

interface LoginRequest {
    email: string;
    password: string;
    emailVerificationCallbackUrl: string;
}

interface LoginResponse {
    accessToken: string;
}

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private apiUrl = 'https://c18-32-ft-java-angular-backend.fly.dev/api';

    constructor(private http: HttpClient) {}

    register(userData: {
        username: string;
        email: string;
        password: string;
        callbackUrl: string;
        redirectUrl: string;
    }): Observable<any> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http
            .post<any>(`${this.apiUrl}/register`, userData, { headers })
            .pipe(
                map((response) => {
                    // Save the access token in the session storage
                    if (response && response.accessToken) {
                        sessionStorage.setItem(
                            'accessToken',
                            response.accessToken
                        );
                    }
                    return response;
                }),
                catchError((error) => {
                    console.error('Registration error:', error);
                    return throwError(error);
                })
            );
    }

    login(data: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(`${this.apiUrl}/login`, data).pipe(
            tap((response: LoginResponse) => {
                // Save the accessToken in sessionStorage
                sessionStorage.setItem('accessToken', response.accessToken);
            })
        );
    }

    getAccessToken(): string | null {
        return sessionStorage.getItem('accessToken');
    }

    emailVerify(data: {
        token: string;
        reactivateCallbackUrl: string;
    }): Observable<{ accessToken: string }> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.post<{ accessToken: string }>(
            `${this.apiUrl}/auth/email-verify`,
            data,
            { headers }
        );
    }

    sendEmailPasswordRecovery(data: {
        email: string;
        callbackUrl: string;
        redirectUrl: string;
    }): Observable<any> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.post(`${this.apiUrl}/auth/recovery`, data, {
            headers,
        });
    }

    changePassword(
        data: { oldPassword: string; newPassword: string },
        token = sessionStorage.getItem('accessToken')
    ): Observable<any> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        });
        return this.http.put<any>(`${this.apiUrl}/auth/change-password`, data, {
            headers,
        });
    }

    changeForgottenPassword(data: {
        token: string;
        newPassword: string;
    }): Observable<any> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.put<any>(
            `${this.apiUrl}/auth/change-forgotten-password`,
            data,
            { headers }
        );
    }
}
