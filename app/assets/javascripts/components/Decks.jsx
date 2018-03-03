import React from 'react';
import Grid from 'material-ui/Grid';
import Typography from 'material-ui/Typography';
import Card, { CardActions, CardContent } from 'material-ui/Card';
import { Link } from 'react-router-dom';

class Decks extends React.Component {
  render() {
    const { decks } = this.props;

    return (
      <div style={{'marginTop': '10px'}}>
        {
          decks.entrySeq().map( entry => {
            const deck = entry[1];
            return (
              <Card style={{ 'textDecoration': 'none' }} component={Link} to={'/decks/' + deck.id} key={deck.id}>
                <CardContent>
                  <Typography variant="headline" component="h2">
                    {deck.name}
                  </Typography>
                  <Typography>{deck.description}</Typography>
                  <Typography component="p">
                    These are some words
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
