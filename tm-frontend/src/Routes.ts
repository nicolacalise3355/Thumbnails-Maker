/* eslint-disable @typescript-eslint/no-explicit-any */
import { ExamplePage } from "./pages";

export interface IAppRoute{
    path: string;
    component: any;
    icon: any;
    title: string;
}

export const AppRoutes: IAppRoute[] = [
    { path: '/example', component: ExamplePage, icon: "Home", title: "Example" },
];