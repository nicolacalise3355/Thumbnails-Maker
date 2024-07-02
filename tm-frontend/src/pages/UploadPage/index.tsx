import React, { useState } from 'react'
import useVideoUploader from '../../hooks/useVideoUploader';
import { useContextDataProvider } from '../../context/DataProvider';
import _costants from '../../costants';
import { ContextInterface } from '../../interfaces/context.interfaces';
import { VideoUploadStatusCard } from '../../atoms';

export const UploadPage = () => {

  const { apiToken } = useContextDataProvider() as ContextInterface;
  const { paths } = _costants;
  const { progress, uploadFile, runSocket } = useVideoUploader(paths.videoUpload, apiToken);

  const [selectedFile, setSelectedFile] = useState<File | null>(null);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedFile(event.target.files?.[0] ?? null);
    runSocket()
  };

  const handleUpload = () => {
    if (selectedFile) {
      uploadFile(selectedFile);
    }
  };

  return (
    <div className='flex gap-12 items-start flex-col sm:items-center'>
      <div className="flex flex-col gap-7 sm:w-full sm:items-center sm:text-center sm:justify-center">
        <div className='flex flex-col gap-2 text-start sm:text-center'>
          <h1 className='font-bold text-3xl'>Upload video and extract thumbnails!</h1>
          <p className='text-lg'>Upload a video to the server to extract some thumbnails, the upload button will be enabled after choosing a video.</p>
        </div>
        <div className='flex flex-col gap-6'>
          <div className='flex flex-col gap-3 sm:items-center'>
            <div className="flex items-center justify-center w-96 sm:w-72">
              <label className="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-gray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600">
                <div className="flex flex-col items-center justify-center pt-5 pb-6">
                  <svg className="w-8 h-8 mb-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 16">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 13h3a3 3 0 0 0 0-6h-.025A5.56 5.56 0 0 0 16 6.5 5.5 5.5 0 0 0 5.207 5.021C5.137 5.017 5.071 5 5 5a4 4 0 0 0 0 8h2.167M10 15V6m0 0L8 8m2-2 2 2" />
                  </svg>
                  <p className="mb-2 text-sm text-gray-500 dark:text-gray-400"><span className="font-semibold">Click to upload</span> or drag and drop</p>
                  <p className="text-xs text-gray-500 dark:text-gray-400">PNG</p>
                </div>
                <input onChange={handleFileChange} id="dropzone-file" type="file" className="hidden" />
              </label>
            </div>
            {selectedFile?.name && 
            <div className='text-start sm:text-center'>
              <h2 className='text-start text-lg font-bold'>File choose:</h2>
              <span className='text-start text-lg'>{selectedFile?.name}</span>
            </div>
            }
            <button disabled={!selectedFile} onClick={handleUpload} className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded w-36 disabled:bg-gray-500'>Upload video</button>
          </div>
        </div>
      </div>
      <div className='flex justify-center max-w-[50%] sm:items-center'>
        <VideoUploadStatusCard status={progress.code} value={progress.value} />
      </div>
    </div>

  )
}
