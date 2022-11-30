import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bhyt from './bhyt';
import BhytDetail from './bhyt-detail';
import BhytUpdate from './bhyt-update';
import BhytDeleteDialog from './bhyt-delete-dialog';

const BhytRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bhyt />} />
    <Route path="new" element={<BhytUpdate />} />
    <Route path=":id">
      <Route index element={<BhytDetail />} />
      <Route path="edit" element={<BhytUpdate />} />
      <Route path="delete" element={<BhytDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BhytRoutes;
