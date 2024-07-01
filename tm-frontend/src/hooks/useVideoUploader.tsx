import { useState } from "react";

const useVideoUploader = (uri: string, token: string) => {
  const [status, setStatus] = useState({ code: 0, value: '' });
  const [progress, setProgress] = useState({ code: 0, value: '' });
  const [result, setResult] = useState(null);


  const [sessionId, setSessionId] = useState<string | null>(null);
  const [socket, setSocket] = useState<WebSocket | null>(null);
  const [messageCount, setMessageCount] = useState(0);

  const runSocket = () => {
      const socket = new WebSocket(`ws://localhost:8080/video?token=${token}`);
      setSocket(socket)
      
      socket.onopen = () => { 
        console.log('Connected to WebSocket');
      };

      socket.onclose = () => {
        console.log('Disconnected from WebSocket');
      };

      socket.onmessage = (event) => {
        const message = event.data;
        if(messageCount === 0){
          setSessionId(message)
        }
        setMessageCount(messageCount + 1)
        setProgress({ code: 1, value: message});
      };
  
      return () => socket.close();
  }

  const closeSocket = () => {
    socket?.close()
  }

  const uploadFile = async (file: File) => {
    setStatus({ code: 1, value: 'Uploading video' });
    const formData = new FormData();
    formData.append('file', file);
    formData.append('session', sessionId ?? '');

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
      setStatus({ code: 2, value: 'Video uploaded' });
    } catch (error) {
      setStatus({ code: -1, value: 'An error occurred in video uploading' });
    }

  };

  return { status, progress, result, uploadFile, runSocket, closeSocket };
};

export default useVideoUploader;
