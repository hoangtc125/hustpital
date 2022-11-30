import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Doctors from './doctors';
import DoctorsDetail from './doctors-detail';
import DoctorsUpdate from './doctors-update';
import DoctorsDeleteDialog from './doctors-delete-dialog';

const DoctorsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Doctors />} />
    <Route path="new" element={<DoctorsUpdate />} />
    <Route path=":id">
      <Route index element={<DoctorsDetail />} />
      <Route path="edit" element={<DoctorsUpdate />} />
      <Route path="delete" element={<DoctorsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DoctorsRoutes;
