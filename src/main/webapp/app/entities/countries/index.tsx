import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Countries from './countries';
import CountriesDetail from './countries-detail';
import CountriesUpdate from './countries-update';
import CountriesDeleteDialog from './countries-delete-dialog';

const CountriesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Countries />} />
    <Route path="new" element={<CountriesUpdate />} />
    <Route path=":id">
      <Route index element={<CountriesDetail />} />
      <Route path="edit" element={<CountriesUpdate />} />
      <Route path="delete" element={<CountriesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CountriesRoutes;
