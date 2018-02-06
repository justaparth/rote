import React from 'react';
import Grid from 'material-ui/Grid';
import Typography from 'material-ui/Typography';
import Card, { CardActions, CardContent } from 'material-ui/Card';


class Home extends React.Component {
  render() {
    return (
      <div style={{'marginTop': '10px'}}>
        <Card>
          <CardContent>
            <Typography variant="headline" component="h2">
              Welcome
            </Typography>
            <Typography>Unfortunately, theres nothing here for now.</Typography>
            <Typography component="p">
              Please check back again later.
            </Typography>
          </CardContent>
        </Card>
      </div>
    );
  }
}

export default Home;
