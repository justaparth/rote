import Dispatcher from '../dispatcher';
import ActionTypes from '../constants';

const CardActions = {

  addNewItem(card) {
    // NOTE: This is a good place for API calls to live.
    Dispatcher.dispatch({
      actionType: ActionTypes.CREATE_CARD,
      card: card
    });
  }

}

export default CardActions;
