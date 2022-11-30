import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cities from './cities';
import CitiesDetail from './cities-detail';
import CitiesUpdate from './cities-update';
import CitiesDeleteDialog from './cities-delete-dialog';

const CitiesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Cities />} />
    <Route path="new" element={<CitiesUpdate />} />
    <Route path=":id">
      <Route index element={<CitiesDetail />} />
      <Route path="edit" element={<CitiesUpdate />} />
      <Route path="delete" element={<CitiesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CitiesRoutes;
