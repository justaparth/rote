import React from 'react';
import { Switch, Route } from 'react-router-dom'

class Home extends React.Component {
  render() {
    return (<p> Home Element </p>)
  }
}

class Reviews extends React.Component {
  render() {
    return (<p> Reviews Element </p>)
  }
}

class Cards extends React.Component {
  render() {
    return (<p> Cards Element </p>)
  }
}

class Main extends React.Component {
    render() {
        return (
          <main>
            <Switch>
              <Route exact path='/' component={Home} />
              <Route path='/reviews' component={Reviews} />
              <Route path='/cards' component={Cards} />
            </Switch>
          </main>
        )
    }
}

export default Main;
