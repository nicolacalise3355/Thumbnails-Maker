import { FC } from "react";
import ExampleTopBar from "../layout/ExampleTopBar";

interface StructureProps {
    children: JSX.Element
}

const AppStructure: FC<StructureProps> = (props) => {
    return(
        <>
            <ExampleTopBar />
            <div className="pt-6 pr-10 pl-10">
                {props.children}
            </div>
        </>
    )
}

export default AppStructure;