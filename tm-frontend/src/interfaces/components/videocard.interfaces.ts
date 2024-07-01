
export interface VideoCardProps {
  id: number;
  title: string;
  uri: string;
  downloadThumbnailCallback?: (id: number) => void; 
  deleteThumbnailCallback?: (id: number) => void;
}

export interface VideoCardData {
  id: number;
  title: string;
  uri: string;
}

export interface VideoUploadStatusCardProps {
  status: number;
  value: string;
}