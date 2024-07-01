import React, { FC } from 'react'
import { VideoUploadStatusCardProps } from '../../interfaces/components/videocard.interfaces'

export const VideoUploadStatusCard: FC<VideoUploadStatusCardProps> = ({ status, value }) => {
  
  const generateStyle = (status: number) => {
    switch(status){
      case 0:
        return { display: 'none' }
      case 1: 
        return { borderColor: '#4299e1', backgroundColor: '#ebf8ff' }
      case 2: 
        return { backgroundColor: '#e6fffa', borderColor: '#38b2ac' }
      case -1:
        return { backgroundColor: '#f56565', borderColor: 'red' }
      default: 
        return {}
    }
  }
  
  return (
    <div style={generateStyle(status)} className="bg-blue-100 border-t border-b px-4 py-3" role="alert">
      <p className="font-bold">Video Upload Status</p>
      <p className="text-sm">{value}.</p>
    </div>
  )
}
