/* eslint-disable @typescript-eslint/no-explicit-any */
import { UploadPage, VideosPage } from "./pages";

export interface IAppRoute{
    path: string;
    component: any;
    icon: any;
    title: string;
}

export const AppRoutes: IAppRoute[] = [
    { path: '/videos', component: VideosPage, icon: "video", title: "Videos" },
    { path: '/upload', component: UploadPage, icon: "upload", title: "Upload" },
];