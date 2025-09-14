import React from 'react';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { useTranslation } from 'react-i18next';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { routesApi } from '../../services/api';
import { Dialog, Transition } from '@headlessui/react';
import { XMarkIcon } from '@heroicons/react/24/outline';
import toast from 'react-hot-toast';

interface AddRouteModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSuccess: () => void;
  serviceId: string;
}

const RouteSchema = Yup.object().shape({
  paths: Yup.array()
    .of(Yup.string().matches(/^\/.*$/, 'Paths must start with /'))
    .min(1, 'At least one path is required'),
  methods: Yup.array()
    .of(Yup.string())
    .min(1, 'At least one HTTP method is required'),
  stripPath: Yup.boolean(),
  preserveHost: Yup.boolean(),
  requestBuffering: Yup.boolean(),
  responseBuffering: Yup.boolean(),
});

const HTTP_METHODS = [
  'GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'HEAD', 'OPTIONS', 'CONNECT', 'TRACE'
];

export const AddRouteModal: React.FC<AddRouteModalProps> = ({
  isOpen,
  onClose,
  onSuccess,
  serviceId,
}) => {
  const { t } = useTranslation();
  const queryClient = useQueryClient();

  const createRouteMutation = useMutation({
    mutationFn: (data: any) => routesApi.createRoute(serviceId, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['routes', serviceId] });
      toast.success(t('routes.routeCreated'));
      onSuccess();
    },
    onError: (error: any) => {
      toast.error(error.response?.data?.message || 'Failed to create route');
    },
  });

  const formik = useFormik({
    initialValues: {
      name: '',
      paths: ['/'],
      methods: ['GET', 'POST', 'PUT', 'DELETE'],
      stripPath: true,
      preserveHost: false,
      requestBuffering: true,
      responseBuffering: true,
    },
    validationSchema: RouteSchema,
    onSubmit: (values) => {
      const routeData = {
        ...values,
        service: { id: serviceId },
        protocols: ['http', 'https'],
      };
      createRouteMutation.mutate(routeData);
    },
  });

  const handleClose = () => {
    formik.resetForm();
    onClose();
  };

  const handlePathChange = (index: number, value: string) => {
    const newPaths = [...formik.values.paths];
    newPaths[index] = value;
    formik.setFieldValue('paths', newPaths);
  };

  const addPath = () => {
    formik.setFieldValue('paths', [...formik.values.paths, '']);
  };

  const removePath = (index: number) => {
    if (formik.values.paths.length > 1) {
      const newPaths = formik.values.paths.filter((_, i) => i !== index);
      formik.setFieldValue('paths', newPaths);
    }
  };

  const toggleMethod = (method: string) => {
    const methods = formik.values.methods;
    if (methods.includes(method)) {
      formik.setFieldValue('methods', methods.filter(m => m !== method));
    } else {
      formik.setFieldValue('methods', [...methods, method]);
    }
  };

  const selectAllMethods = () => {
    formik.setFieldValue('methods', HTTP_METHODS);
  };

  const clearAllMethods = () => {
    formik.setFieldValue('methods', []);
  };

  return (
    <Transition appear show={isOpen} as={React.Fragment}>
      <Dialog as="div" className="relative z-10" onClose={handleClose}>
        <Transition
          as={React.Fragment}
          enter="ease-out duration-300"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="ease-in duration-200"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <div className="fixed inset-0 bg-black bg-opacity-25" />
        </Transition>

        <div className="fixed inset-0 overflow-y-auto">
          <div className="flex min-h-full items-center justify-center p-4 text-center">
            <Transition
              as={React.Fragment}
              enter="ease-out duration-300"
              enterFrom="opacity-0 scale-95"
              enterTo="opacity-100 scale-100"
              leave="ease-in duration-200"
              leaveFrom="opacity-100 scale-100"
              leaveTo="opacity-0 scale-95"
            >
              <div className="w-full max-w-2xl transform overflow-hidden rounded-2xl bg-white p-6 text-left align-middle shadow-xl transition-all">
                <div className="flex items-center justify-between mb-4">
                  <h3 className="text-lg font-medium leading-6 text-gray-900">
                    {t('routes.addRoute')}
                  </h3>
                  <button
                    type="button"
                    className="text-gray-400 hover:text-gray-600"
                    onClick={handleClose}
                  >
                    <XMarkIcon className="h-6 w-6" />
                  </button>
                </div>

                <form onSubmit={formik.handleSubmit} className="space-y-6">
                  <div>
                    <label htmlFor="name" className="block text-sm font-medium text-gray-700">
                      Route Name (Optional)
                    </label>
                    <input
                      type="text"
                      id="name"
                      name="name"
                      value={formik.values.name}
                      onChange={formik.handleChange}
                      className="mt-1 block w-full rounded-md border border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 sm:text-sm"
                      placeholder="my-route"
                    />
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      {t('routes.paths')}
                    </label>
                    {formik.values.paths.map((path, index) => (
                      <div key={`path-${index}-${path}`} className="flex space-x-2 mb-2">
                        <input
                          type="text"
                          value={path}
                          onChange={(e) => handlePathChange(index, e.target.value)}
                          className="flex-1 rounded-md border border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 sm:text-sm"
                          placeholder="/api/v1/users"
                        />
                        {formik.values.paths.length > 1 && (
                          <button
                            type="button"
                            onClick={() => removePath(index)}
                            className="px-3 py-2 text-red-600 hover:text-red-800"
                          >
                            Remove
                          </button>
                        )}
                      </div>
                    ))}
                    <button
                      type="button"
                      onClick={addPath}
                      className="text-sm text-primary-600 hover:text-primary-800"
                    >
                      + Add Path
                    </button>
                    {formik.touched.paths && formik.errors.paths && (
                      <p className="mt-1 text-sm text-red-600">{formik.errors.paths}</p>
                    )}
                  </div>

                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      {t('routes.methods')}
                    </label>
                    <div className="flex space-x-2 mb-2">
                      <button
                        type="button"
                        onClick={selectAllMethods}
                        className="text-sm text-primary-600 hover:text-primary-800"
                      >
                        Select All
                      </button>
                      <button
                        type="button"
                        onClick={clearAllMethods}
                        className="text-sm text-gray-600 hover:text-gray-800"
                      >
                        Clear All
                      </button>
                    </div>
                    <div className="grid grid-cols-3 gap-2">
                      {HTTP_METHODS.map((method) => (
                        <label key={method} className="flex items-center">
                          <input
                            type="checkbox"
                            checked={formik.values.methods.includes(method)}
                            onChange={() => toggleMethod(method)}
                            className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300 rounded"
                          />
                          <span className="ml-2 text-sm text-gray-700">{method}</span>
                        </label>
                      ))}
                    </div>
                    {formik.touched.methods && formik.errors.methods && (
                      <p className="mt-1 text-sm text-red-600">{formik.errors.methods}</p>
                    )}
                  </div>

                  <div className="space-y-4">
                    <div className="flex items-center">
                      <input
                        type="checkbox"
                        id="stripPath"
                        name="stripPath"
                        checked={formik.values.stripPath}
                        onChange={formik.handleChange}
                        className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300 rounded"
                      />
                      <label htmlFor="stripPath" className="ml-2 text-sm text-gray-700">
                        {t('routes.stripPath')}
                      </label>
                    </div>

                    <div className="flex items-center">
                      <input
                        type="checkbox"
                        id="preserveHost"
                        name="preserveHost"
                        checked={formik.values.preserveHost}
                        onChange={formik.handleChange}
                        className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300 rounded"
                      />
                      <label htmlFor="preserveHost" className="ml-2 text-sm text-gray-700">
                        {t('routes.preserveHost')}
                      </label>
                    </div>

                    <div className="flex items-center">
                      <input
                        type="checkbox"
                        id="requestBuffering"
                        name="requestBuffering"
                        checked={formik.values.requestBuffering}
                        onChange={formik.handleChange}
                        className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300 rounded"
                      />
                      <label htmlFor="requestBuffering" className="ml-2 text-sm text-gray-700">
                        {t('routes.requestBuffering')}
                      </label>
                    </div>

                    <div className="flex items-center">
                      <input
                        type="checkbox"
                        id="responseBuffering"
                        name="responseBuffering"
                        checked={formik.values.responseBuffering}
                        onChange={formik.handleChange}
                        className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300 rounded"
                      />
                      <label htmlFor="responseBuffering" className="ml-2 text-sm text-gray-700">
                        {t('routes.responseBuffering')}
                      </label>
                    </div>
                  </div>

                  <div className="bg-blue-50 p-3 rounded-md">
                    <p className="text-sm text-blue-800">
                      💡 {t('routes.performanceTip')}
                    </p>
                  </div>

                  <div className="flex justify-end space-x-3 pt-4">
                    <button
                      type="button"
                      onClick={handleClose}
                      className="btn-secondary"
                    >
                      {t('common.cancel')}
                    </button>
                    <button
                      type="submit"
                      disabled={createRouteMutation.isPending}
                      className="btn-primary disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                      {createRouteMutation.isPending ? 'Creating...' : t('common.create')}
                    </button>
                  </div>
                </form>
              </div>
            </Transition>
          </div>
        </div>
      </Dialog>
    </Transition>
  );
};
