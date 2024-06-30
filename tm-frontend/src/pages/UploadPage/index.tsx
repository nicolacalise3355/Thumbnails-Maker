import React, { useState } from 'react'
import useVideoUploader from '../../hooks/useVideoUploader';
import { useContextDataProvider } from '../../context/DataProvider';
import _costants from '../../costants';
import { ContextInterface } from '../../interfaces/context.interfaces';

export const UploadPage = () => {

  const { apiToken } = useContextDataProvider() as ContextInterface;
  const { paths } = _costants;
  const { status, progress, result, uploadFile } = useVideoUploader(paths.videoUpload,apiToken);

  const [selectedFile, setSelectedFile] = useState<File | null>(null);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedFile(event.target.files?.[0] ?? null);
  };

  const handleUpload = () => {
    if (selectedFile) {
      uploadFile(selectedFile);
    }
  };

  return (
    <div className="flex flex-col gap-7">
      <div className='flex'>
        <h1 className='font-bold text-3xl'>Upload video and extract thumbnails!</h1>
      </div>
      <div className='flex flex-col gap-6'>
        <div className='flex gap-3'>
          <input onChange={handleFileChange} className="text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400" id="file_input" type="file" />
          <button onClick={handleUpload} className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'>Upload video</button>
        </div>
        {progress &&
          <div>Sezione status {status}</div>
        }
        
      {status === 'uploading' && <p>Uploading...</p>}
      {progress && <p>Progress: {progress}</p>}
      {result && <p>{JSON.stringify(result)}</p>}
      {status === 'error' && <p>Errore durante il caricamento del video.</p>}
      </div>
    </div>
  )
}
