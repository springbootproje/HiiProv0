export interface User {
    id: string;
    fullName: string;
    email: string;
    password: string;
}

export interface UserLogin {
    email: string;
    password: string;
}
