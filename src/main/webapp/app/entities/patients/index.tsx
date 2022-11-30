import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Patients from './patients';
import PatientsDetail from './patients-detail';
import PatientsUpdate from './patients-update';
import PatientsDeleteDialog from './patients-delete-dialog';

const PatientsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Patients />} />
    <Route path="new" element={<PatientsUpdate />} />
    <Route path=":id">
      <Route index element={<PatientsDetail />} />
      <Route path="edit" element={<PatientsUpdate />} />
      <Route path="delete" element={<PatientsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PatientsRoutes;
