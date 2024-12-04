import { createContext } from "react";
import { IContext } from "./authTypes";

export const AuthContext = createContext<IContext>({} as IContext);


