import React from 'react';
import { HashRouter } from 'react-router-dom'
import { render } from 'react-dom';
import RoteApp from './RoteApp.jsx';

import '../stylesheets/style.scss'

render(
  (
    <HashRouter>
      <RoteApp />
    </HashRouter>
  ), document.getElementById("root")
);
