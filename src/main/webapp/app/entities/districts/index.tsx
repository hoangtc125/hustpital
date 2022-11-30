import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Districts from './districts';
import DistrictsDetail from './districts-detail';
import DistrictsUpdate from './districts-update';
import DistrictsDeleteDialog from './districts-delete-dialog';

const DistrictsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Districts />} />
    <Route path="new" element={<DistrictsUpdate />} />
    <Route path=":id">
      <Route index element={<DistrictsDetail />} />
      <Route path="edit" element={<DistrictsUpdate />} />
      <Route path="delete" element={<DistrictsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DistrictsRoutes;
