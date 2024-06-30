import { useEffect, useState } from "react";

const useVideoUploader = (uri: string, token: string) => {
  const [status, setStatus] = useState('idle');
  const [progress, setProgress] = useState(null);
  const [result, setResult] = useState(null);

  const [start, setStart] = useState(false)

  useEffect(() => {
    if(start) {
      const socket = new WebSocket('ws://localhost:8080/wsconnection');

      socket.onopen = () => {
        socket.send(JSON.stringify({
          type: 'Authorization',
          token: token,
        }));
      };

      socket.onmessage = (event) => {
        const message = event.data;
        setProgress(message);
      };
  
      return () => socket.close();
    }
  }, [start]);

  const uploadFile = async (file: File) => {
    setStart(true)
    setStatus('uploading');
    const formData = new FormData();
    formData.append('file', file);

    try {
      const response = await fetch(uri, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`
        },
        body: formData
      });

      const data = await response.json();
      setResult(data);
      setStatus('completed');
      setStart(false)
    } catch (error) {
      setStatus('error');
      setStart(false)
    }
  };

  return { status, progress, result, uploadFile };
};

export default useVideoUploader;
