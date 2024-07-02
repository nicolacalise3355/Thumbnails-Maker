import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useContextDataProvider } from "../../context/DataProvider";
import _costants from "../../costants";
import { HTTP_API } from "../../services";
import { ErrorToast, Spinner } from "../../atoms";
import { LoginReponse, Response } from "../../interfaces/api.interfaces";
import { ContextInterface } from "../../interfaces/context.interfaces";

export const LoginPage = () => {

  const { setApiToken } = useContextDataProvider() as ContextInterface;
  const { paths, http_req, option_header } = _costants;

  const [loginResult, setLoginResult] = React.useState<Response | undefined>(undefined);
  const [error, setError] = React.useState<string | undefined>(undefined)
  const [isLoading, setIsLoading] = React.useState(false);

  const [username, setUsername] = React.useState('')
  const [password, setPassword] = React.useState('')

  
  const navigate = useNavigate();

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const data = {
      username, password
    }
    HTTP_API(http_req.POST,paths.auth,data,setLoginResult,setIsLoading, option_header)
  };

  useEffect(() => {
    if(loginResult?.success){
      setApiToken((loginResult.data as LoginReponse).token);
      navigate('videos')
    }
    if(loginResult && !loginResult.success){
      setError('Login errato');
    }
  },[loginResult])

  return (
    <div className="flex sm:w-full min-h-full flex-1 flex-col justify-center items-center px-6 py-12 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-sm">
        <h2 className="mt-10 text-center text-3xl font-bold leading-9 tracking-tight text-gray-900">
          Thumbnails Maker!
        </h2>
      </div>
      <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm w-[460px]">
        <form className="space-y-6" onSubmit={handleSubmit}>
          <div>
            <div className="flex items-center justify-between">
              <label
                htmlFor="username"
                className="block text-sm font-medium leading-6 text-gray-900"
              >
                Username
              </label>
            </div>

            <div className="mt-2">
              <input
                id="username"
                name="username"
                type="text"
                required
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                className="p-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>

          <div>
            <div className="flex items-center justify-between">
              <label
                htmlFor="password"
                className="block text-sm font-medium leading-6 text-gray-900"
              >
                Password
              </label>
            </div>
            <div className="mt-2">
              <input
                id="password"
                name="password"
                type="password"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="p-3 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
              />
            </div>
          </div>

          <div className="flex flex-col gap-7 justify-center items-center">
            <button
              type="submit"
              className="flex w-full justify-center rounded-md bg-slate-700 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-slate-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
            >
              Login
            </button>
            {isLoading && <Spinner />}
            {error && <ErrorToast title="Login Error" message="An error occurs after your login attempt!" />}
          </div>
        </form>
      </div>
    </div>
  );
};
