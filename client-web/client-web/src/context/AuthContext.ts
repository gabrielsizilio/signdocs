import { createContext } from "react";
import { IContext } from "./AuthProvider/authTypes";

export const AuthContext = createContext<IContext>({} as IContext);


