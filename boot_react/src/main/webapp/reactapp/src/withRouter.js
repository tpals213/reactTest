import { useNavigate, useLocation, useParams } from 'react-router-dom';
import React from 'react';

export const withRouter = (Component) => {
  function ComponentWithRouterProp(props) {
    let navigate = useNavigate();
    let location = useLocation();
    let params = useParams();
    return (
      <Component
        {...props}
        navigate={navigate}
        location={location}
        params={params}
      />
    );
  }

  return ComponentWithRouterProp;
};
