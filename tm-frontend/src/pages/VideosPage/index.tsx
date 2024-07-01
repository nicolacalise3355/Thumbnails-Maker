import { useState } from 'react'
import { useContextDataProvider } from '../../context/DataProvider'
import { ContextInterface } from '../../interfaces/context.interfaces'
import _costants from '../../costants';
import useFetch from '../../hooks/useFetch';
import { Spinner } from '../../atoms';
import { DataResponse, Response } from '../../interfaces/api.interfaces';
import { ModalDownloadThumbnail, VideoCard } from '../../components';
import { VideoCardData } from '../../interfaces/components/videocard.interfaces';
import { HTTP_API } from '../../services';

export const VideosPage = () => {

  const { apiToken } = useContextDataProvider() as ContextInterface;
  const { paths, http_req } = _costants;
  const { data, error, isLoading, reloadFetch } = useFetch(paths.videos, http_req.GET, apiToken)

  const [openModalDownload, setOpenModalDownload] = useState(false)
  const [videoSelected, setVideoSelected] = useState(0);
  const [loadingDelete, setLoadingDelete] = useState(false);

  const clickDownloadThumbnail = (id: number) => {
    setVideoSelected(id);
    setOpenModalDownload(true);
  }

  const onDelete = (r: Response) => {
    if(r) {
      reloadFetch()
    }
  }

  const deleteThumbnail = (id: number) => {
    HTTP_API(
      http_req.DELETE, 
      `${paths.video}${id}`, 
      undefined, 
      onDelete,
      setLoadingDelete, 
      { 'Authorization': `Bearer ${apiToken}` }
    )
  }

  const closeModal = () => setOpenModalDownload(false);

  const downloadThumbnail = (w: number | null, h: number | null) => {
    setOpenModalDownload(false)
    if(!w || !h){
      window.open(`${paths.thumbnail}${videoSelected}`, '_blank')
    }else{
      window.open(`${paths.thumbnail}${videoSelected}?width=${w}&height=${h}`, '_blank')
    }
  }

  return (
    <>
      {(isLoading || loadingDelete) && <Spinner />}
      {error && <div>Errore</div>}
      {data && 
        <div className="flex flex-col gap-7">
          <div className='flex'>
            <h1 className='font-bold text-4xl'>Get all the video thumbnails!</h1>
          </div>
          <div className='flex flex-col gap-6'>
            {((data as DataResponse).value as VideoCardData[]).map((v: VideoCardData) => (
              <VideoCard key={`vcc-${v.id}`} id={v.id} title={v.title} uri={v.uri} downloadThumbnailCallback={clickDownloadThumbnail} deleteThumbnailCallback={deleteThumbnail}/>
            ))}
          </div>
        </div>
      }
      {openModalDownload && <ModalDownloadThumbnail onClose={closeModal} onAccept={downloadThumbnail} />}
    </>
  )
}


