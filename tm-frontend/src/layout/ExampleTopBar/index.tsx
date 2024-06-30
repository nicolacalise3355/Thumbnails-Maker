import { AppRoutes, IAppRoute } from '../../Routes'
import { useNavigate } from 'react-router-dom';

const ExampleTopBar = () => {

  const navigate = useNavigate();

  const logout = () => {
    navigate('/');
  }

  return (
    <header className="bg-slate-200">
      <nav className="mx-auto flex items-center justify-between pl-8 pr-16 py-4" aria-label="Global">
        <div className="flex lg:flex-1">
          <a href="" className="">
            <img className="h-8 w-auto" src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600" alt="" />
          </a>
        </div>
        <ul className='flex gap-6'>
          {
            AppRoutes.map((r: IAppRoute) => (
              <button key={`${r.title}-btn`} className='bg-gray-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded-l' onClick={() => navigate(r.path)}>
                {r.title}
              </button>
            ))
          }
          <button className='bg-gray-100 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded-l' onClick={() => logout()}>
            Logout
          </button>
        </ul>
      </nav>
    </header>
  )
}

export default ExampleTopBar