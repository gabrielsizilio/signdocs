import { IUser } from "../context/authTypes";

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