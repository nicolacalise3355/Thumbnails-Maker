import React from 'react'
import { useContextDataProvider } from '../../context/DataProvider'
import { ContextInterface } from '../../interfaces/context.interfaces'
import _costants from '../../costants';
import useFetch from '../../hooks/useFetch';
import { Spinner } from '../../atoms';
import { DataResponse } from '../../interfaces/api.interfaces';
import { VideoCard } from '../../components';
import { VideoCardData } from '../../interfaces/components/videocard.interfaces';

export const VideosPage = () => {

  const { apiToken } = useContextDataProvider() as ContextInterface;
  const { paths, http_req } = _costants;
  const { data, error, isLoading } = useFetch(paths.videos, http_req.GET, apiToken)

  const downloadThumbnails = (id: number) => {
    console.log(id)
  }

  return (
    <>
      {isLoading && <Spinner />}
      {error && <div>Errore</div>}
      {data && 
        <div className="flex flex-col gap-7">
          <div className='flex'>
            <h1 className='font-bold text-4xl'>Get all the video thumbnails!</h1>
          </div>
          <div className='flex flex-col gap-6'>
            {((data as DataResponse).value as VideoCardData[]).map((v: VideoCardData) => (
              <VideoCard key={`vcc-${v.id}`} id={v.id} title={v.title} uri={v.uri} downloadThumbnailCallback={downloadThumbnails}/>
            ))}
          </div>
        </div>
      }
    </>
  )
}


