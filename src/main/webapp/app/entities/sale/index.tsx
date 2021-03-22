import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Sale from './sale';
import SaleDetail from './sale-detail';
import SaleUpdate from './sale-update';
import SaleDeleteDialog from './sale-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SaleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SaleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SaleDetail} />
      <ErrorBoundaryRoute path={match.url} component={Sale} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SaleDeleteDialog} />
  </>
);

export default Routes;
