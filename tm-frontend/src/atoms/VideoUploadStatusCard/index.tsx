import { FC } from 'react'
import { VideoUploadStatusCardProps } from '../../interfaces/components/videocard.interfaces'

export const VideoUploadStatusCard: FC<VideoUploadStatusCardProps> = ({ status, value }) => {
  
  const generateStyle = (status: number) => {
    switch(status){
      case 0:
        return { display: 'none' }
      case 1 && 3: 
        return { borderColor: '#4299e1', backgroundColor: '#ebf8ff', borderWidth: '2px' }
      case 2 && 4: 
        return { backgroundColor: '#e6fffa', borderColor: '#38b2ac', borderWidth: '2px' }
      case -1:
        return { backgroundColor: '#f56565', borderColor: 'red', borderWidth: '2px' }
      default: 
        return {}
    }
  }
  
  return (
    <div style={generateStyle(status)} className="bg-blue-100 border-solid rounded p-6" role="alert">
      <p className="font-bold">Video Upload Status</p>
      <p className="text-sm">{value}.</p>
    </div>
  )
}
