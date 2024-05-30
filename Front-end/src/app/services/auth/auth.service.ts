import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class AuthService {
    private apiUrl = 'https://c18-32-ft-java-angular-backend.fly.dev/api';

    constructor(private http: HttpClient) {}

    register(data: {
        username: string;
        email: string;
        password: string;
        callbackUrl: string;
        redirectUrl: string;
    }): Observable<any> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.post(`${this.apiUrl}/auth/register`, data, {
            headers,
        });
    }

    login(data: {
        email: string;
        password: string;
        emailVerificationCallbackUrl: string;
    }): Observable<any> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.post(`${this.apiUrl}/auth/login`, data, { headers });
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
    }): Observable<{ message: string }> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.post<{ message: string }>(
            `${this.apiUrl}/auth/recovery`,
            data,
            {
                headers,
            }
        );
    }

    changePassword(
        data: { oldPassword: string; newPassword: string },
        token: string
    ): Observable<any> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        });
        return this.http.put<any>(`${this.apiUrl}/auth/change-password`, data, {
            headers,
        });
    }

    changeForgottenPassword(data:{
        token: string,
        newPassword: string}
    ): Observable<any> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.put<any>(
            `${this.apiUrl}/auth/change-forgotten-password`,
            data,
            { headers }
        );
    }
}
