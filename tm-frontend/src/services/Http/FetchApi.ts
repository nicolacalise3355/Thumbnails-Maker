/* eslint-disable @typescript-eslint/no-explicit-any */
import { Response } from "../../interfaces/api.interfaces";

/**
 *
 * @param {*} isPositive if response of the server is positive
 * @param {*} data data to set
 * @returns Object structure
 */
const makeResponse = (isPositive: boolean, data: any): Response => {
  return {
    success: isPositive ?? false,
    data
  };
};

/**
 *
 * @param {*} method http method used
 * @param {*} url url to send the request
 * @param {*} data config of the request
 * @returns Fetch result
 */
export const sendHttpRequest = (method: string, url: string, data: any, headers: any = undefined) => {
  const fetchOptions: RequestInit = {
    method,
    headers: headers,
    body: JSON.stringify(data)
  };
  return fetch(url, fetchOptions)
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      return response.json();
    })
    .catch(error => {
      console.error('Fetch Error:', error);
      throw error;
    });
};

/**
 *
 * @param {*} method http method used
 * @param {*} url url to send the request
 * @param {*} data config of the request
 * @param {*} options options of the request
 * @param {*} callbackResult function to set the result
 * @param {*} callbackLoading function to set if we are waiting for the result
 */
export const HTTP_API = (
  method: string,
  url: string,
  data: any,
  callbackResult: (arg0: Response) => void,
  callbackLoading: ((arg0: boolean) => void) | undefined = undefined,
  options: any = undefined,
) => {
  if (callbackLoading) callbackLoading(true);
  sendHttpRequest(method, url, data, options)
    .then(responseData => {
      callbackResult(makeResponse(true, responseData));
      if (callbackLoading) callbackLoading(false);
    })
    .catch(error => {
      console.log(error);
      callbackResult(makeResponse(false, error));
      if (callbackLoading) callbackLoading(false);
    });
};
