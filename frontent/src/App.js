import React from 'react';
import './App.css';
import Page from './components/Page';
import Nav from './components/Nav';
import SignIn from './components/SignIn';
import Profile from './components/Profile';
import NotFound from './components/NotFound';
import CodePage from './components/CodePage';
import { Route,  BrowserRouter as Router } from 'react-router-dom';
import { withRouter } from "react-router";
import 'antd/dist/antd.css';

function App() {
  return (
    <Router >
      <Nav />
      <div>
        <Route exact path={"/login"} component={withRouter(SignIn)} />
        <Route exact path={["/home", "/"]} component={withRouter(Page)} />
        <Route exact path={"/profile"} component={withRouter(Profile)} />
        <Route exact path={"/question/:id"} component={withRouter(CodePage)} />
        {/* <Route exact path='*' component={NotFound} /> */}
      </div>
      </Router>
  );
}

export default App;
