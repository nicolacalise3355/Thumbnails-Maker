import { FC } from "react";
import ExampleTopBar from "../layout/ExampleTopBar";

interface StructureProps {
    children: JSX.Element
}

const AppStructure: FC<StructureProps> = (props) => {
    return(
        <>
            <ExampleTopBar />
            <div className="pt-6 pr-10 pl-10 sm:pt-5 sm:pr-3 sm:pl-2">
                {props.children}
            </div>
        </>
    )
}

export default AppStructure;