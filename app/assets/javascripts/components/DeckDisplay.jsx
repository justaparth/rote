import React from 'react';
import Grid from 'material-ui/Grid';
import Typography from 'material-ui/Typography';
import Card, { CardActions, CardContent } from 'material-ui/Card';
import { withStyles } from 'material-ui/styles';
import Button from 'material-ui/Button';
import DeckActions from '../actions/deckActions';

const styles = theme => ({
  card: {
    minWidth: 275,
  },
  bullet: {
    display: 'inline-block',
    margin: '0 2px',
    transform: 'scale(0.8)',
  },
  title: {
    marginBottom: 16,
    fontSize: 14,
    color: theme.palette.text.secondary,
  },
  pos: {
    marginBottom: 12,
    color: theme.palette.text.secondary,
  },
});

class DeckDisplay extends React.Component {

  componentWillMount() {
    DeckActions.loadDeck(parseInt(this.props.match.params.deckId));
  }

  render() {
    const { classes, match, decks } = this.props;
    const deckId = parseInt(match.params.deckId);
    const deck = decks.get(deckId);
    const cards = (deck && deck.cards) || [];
    console.log('hello');
    console.log(cards);
    return (
      <div>
        { cards.map( (card) => {
          return (
          <div key={card.id}>
            <Card className={classes.card}>
              <CardContent>
                <Typography variant="headline" component="h2">
                  {card.japanese}
                </Typography>
                <Typography className={classes.pos}>{card.furigana}</Typography>
                <Typography component="p">
                  {card.english.join(', ')}
                </Typography>
              </CardContent>
            </Card>
          </div>
        );
        })
      }
      </div>
    );
  }
}

export default withStyles(styles)(DeckDisplay);
