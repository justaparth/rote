import React from 'react';
import Grid from 'material-ui/Grid';
import Typography from 'material-ui/Typography';
import Card, { CardActions, CardContent } from 'material-ui/Card';
import { Link } from 'react-router-dom';

class Decks extends React.Component {
  render() {
    const { decks } = this.props;
    const a = [1, 2, 3];

    return (
      <div style={{'marginTop': '10px'}}>
        {
          decks.entrySeq().map( x => {
            return (
              <Card style={{ 'textDecoration': 'none' }} component={Link} to={'/decks/' + x[0]} key={x[0]}>
                <CardContent>
                  <Typography variant="headline" component="h2">
                    Sample Deck
                  </Typography>
                  <Typography>日本語能力試験N２語彙練習</Typography>
                  <Typography component="p">
                    These are the words from that impossibly hard textbook.
                  </Typography>
                </CardContent>
              </Card>
            );
          })
        }
      </div>
    );
  }
}

export default Decks;
