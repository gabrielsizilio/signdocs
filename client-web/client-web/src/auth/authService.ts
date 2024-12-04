import api from "../api/api"

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
