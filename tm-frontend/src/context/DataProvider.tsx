import { createContext, useContext, useState } from "react";

interface ContextProps {
    children: JSX.Element
}

export const ctx = createContext({});

export const DataProvider = ({children} : ContextProps) => {

    const [apiToken, setApiToken] = useState("");

    return (
        <ctx.Provider value={{ 
            apiToken,
            setApiToken
        }}>
            {children}
        </ctx.Provider>
    )
}

export const useContextDataProvider = () => useContext(ctx);