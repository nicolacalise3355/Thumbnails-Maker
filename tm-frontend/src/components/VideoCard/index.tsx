
import React, { FC } from 'react'
import { VideoCardProps } from '../../interfaces/components/videocard.interfaces'

export const VideoCard: FC<VideoCardProps> = ({ id, title, uri, downloadThumbnailCallback, deleteThumbnailCallback }) => {
  return (
    <div key={`vc-${id}`} className="flex bg-gray-300 rounded-xl p-4 justify-between">
      <div className='flex gap-7 items-center'>
        <p className='text-xl sm:text-base font-bold'>{title}</p>
        <a className='sm:hidden' href={uri}>{uri}</a>
      </div>
      <div className='flex gap-5'>
        <button onClick={() => deleteThumbnailCallback ? deleteThumbnailCallback(id) : undefined} className='bg-gray-700 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded-full'>Delete</button>
        <button onClick={() => downloadThumbnailCallback ? downloadThumbnailCallback(id) : undefined} className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full'>Download</button>
      </div>
    </div>
  )
}
