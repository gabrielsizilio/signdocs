import api from "../api/api"
import { IUser } from "../context/AuthProvider/authTypes";

export async function loginRequest(email: string, password: string) {
    try {
        const response = await api.post('auth/login', { email, password });
        return response.data;
    } catch (error) {

        console.log(error);
        // TODO: Fazer return response para erro
        return null;
    }

}

export function setUserLocalStorage(user: IUser | null) {
    localStorage.setItem("tk", JSON.stringify(user));
}

export function getUserLocalStorage() {
    const json = localStorage.getItem("tk");

    if (!json) {
        return null;
    }

    const user = JSON.parse(json);
    return user ?? null;
}