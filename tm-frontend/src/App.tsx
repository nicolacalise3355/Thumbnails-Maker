
import './App.css'
import { AppRoutes } from './Routes'
import { DataProvider } from './context/DataProvider'
import { FallbackPage } from './pages'
import AppStructure from './structure/ApplicationStructure'
import ErrorBoundary from './structure/ErrorBoundary'
import { BrowserRouter, Route, Routes } from 'react-router-dom'

function App() {

  return (
    <ErrorBoundary>
        <DataProvider>
          <BrowserRouter>
            <Routes>
              {AppRoutes.map((route) => {
                  return(
                    <Route key={route.path} path={route.path} element={
                      <AppStructure> 
                        <route.component />
                      </AppStructure>
                    } />
                  )
                })}
              <Route path='/fallback' element={
                <FallbackPage />
              }/>
            </Routes>
          </BrowserRouter>
        </DataProvider>
      </ErrorBoundary>
  )
}

export default App
