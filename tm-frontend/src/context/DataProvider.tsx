import { createContext, useContext, useState } from "react";

interface ContextProps {
    children: JSX.Element
}

export const ctx = createContext({});

export const DataProvider = ({children} : ContextProps) => {

    const [api_token, setApiToken] = useState("");

    return (
        <ctx.Provider value={{ 
            api_token,
            setApiToken
        }}>
            {children}
        </ctx.Provider>
    )
}

export const useContextDataProvider = () => useContext(ctx);