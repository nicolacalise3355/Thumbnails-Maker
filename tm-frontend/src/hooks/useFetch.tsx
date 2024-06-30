import { useState, useEffect } from 'react';
//import _costants from '../costants';

const useFetch = (url: string, method: string = 'GET', token: string = '') => {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const [data, setData] = useState<any>(null);
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  //const { option_header } = _costants;

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      setError(null);

      try {
        const options: RequestInit = {
            method: method,
            headers: {
              'Authorization': `Bearer ${token}`
            }
          };
        const response = await fetch(url, options); 
        if (!response.ok) {
          throw new Error('HTTP Error.');
        }
        const responseData = await response.json();
        setData(responseData); 
      } catch (error) {
        setError('Errore');
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, [url, method, token]);

  return { data, error, isLoading };
};

export default useFetch;