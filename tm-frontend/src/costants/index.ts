const port = 8080;
const host = `http://localhost:${port}`

const option_header = {
  Accept: 'application/json',
  'Content-Type': 'application/json',
}

const paths = {
  httpServerUrl: host,
  auth: `${host}/auth/login`,
  videos: `${host}/api/v1/video/list`,
  videoUpload: `${host}/api/v1/video/upload`
}

const http_req = {
    GET: "GET",
    POST: "POST",
    PUT: "PUT",
    DELETE: "DELETE"
}

const _costants = { paths, http_req, option_header }

export default _costants;