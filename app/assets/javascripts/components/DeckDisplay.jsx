import React from 'react';
import Grid from 'material-ui/Grid';
import Typography from 'material-ui/Typography';
import Card, { CardActions, CardContent } from 'material-ui/Card';

class DeckDisplay extends React.Component {
  render() {
    const testData = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

    return (
      <div style={{'marginTop': '10px'}}>
        {
          testData.map(x => {
            return <p key={x}> hello {x} </p>;
          })
        }
      </div>
    );
  }
}

export default DeckDisplay;
