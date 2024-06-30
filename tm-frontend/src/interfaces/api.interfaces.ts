export type Response = {
    success: boolean,
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    data: any
}

export interface LoginReponse {
    token: string;
    expiresIn: number;
}