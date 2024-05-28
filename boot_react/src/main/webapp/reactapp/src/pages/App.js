// 필요한 모듈들을 불러옴
import 'bootstrap/dist/css/bootstrap.css'
import '../styles/global.css'
import Head from 'next/head';
// 리액트 쿼리(Router 설정을 최소화하기 위함)에서 필요한 모듈을 추가로 불러옴
import {QueryClient, QueryClientProvider} from 'react-query';
import NavigationBar from "../components/common/navigationBar"
import { Container, ThemeProvider } from 'react-bootstrap';
import {authStore} from '../stores/authStore';

// QueryClient 인스턴스 생성함. 이 인스턴스를 통해 쿼리 설정과 상태를 관리함
const queryClient = new QueryClient();


const App = ({ Component, pageProps }) => {
  return (
    // QueryClientProvider 컴포넌트로 전체 앱을 감싸줌
    // 이는 애플리케이션의 모든 부분에서 리액트 쿼리를 사용할 수 있도록 하겠다는것
    // 라우터로 페이지 연결하고 새로고침 처리를 리액트 쿼리가 관리하게 됨
    <QueryClientProvider client={queryClient}>
      <ThemeProvider
        breakpoints={['xxxl', 'xxl', 'xl', 'lg', 'md', 'sm', 'xs', 'xxs']}
        minBreakpoint='sm'>
      <Head>
        <link rel='icon' href='/images/favicon.ico' />
        <title>Boot_React</title>
      </Head>
      <Container fluid className='navigation-container'>
        <NavigationBar />
      </Container>
      <Container fluid className='flex-container'>
        <Component {...pageProps} authStore={authStore} />
      </Container>
      </ThemeProvider>
    </QueryClientProvider>
  );
};

export default App;
