import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ethnics from './ethnics';
import EthnicsDetail from './ethnics-detail';
import EthnicsUpdate from './ethnics-update';
import EthnicsDeleteDialog from './ethnics-delete-dialog';

const EthnicsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ethnics />} />
    <Route path="new" element={<EthnicsUpdate />} />
    <Route path=":id">
      <Route index element={<EthnicsDetail />} />
      <Route path="edit" element={<EthnicsUpdate />} />
      <Route path="delete" element={<EthnicsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EthnicsRoutes;
