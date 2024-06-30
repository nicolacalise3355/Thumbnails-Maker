export type Response = {
    success: boolean,
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    data: any
}

export type DataObjectValue = object[] | object;

export interface LoginReponse {
    token: string;
    expiresIn: number;
}

export interface DataResponse {
  statusCode: number;
  value: DataObjectValue;
}